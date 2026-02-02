const { Pool } = require("pg");
const fs = require("fs");
const path = require("path");

const pool = new Pool({
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  database: process.env.DB_NAME,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
});

exports.createPost = async (req, res) => {
  const { caption } = req.body;
  const file = req.file;

  if (!file) return res.status(400).json({ message: "Imagem obrigatória" });

  try {
    const userId = req.user.id; 
    
    const uploadDir = path.join(process.cwd(), "uploads");
    
    if (!fs.existsSync(uploadDir)) {
      fs.mkdirSync(uploadDir, { recursive: true });
    }

    const fileName = `${Date.now()}-${file.originalname || 'image.jpg'}`;
    const filePath = path.join(uploadDir, fileName);

    fs.writeFileSync(filePath, file.buffer);

    const imageUrl = fileName; 

    await pool.query(
      "INSERT INTO posts (user_id, image_url, caption) VALUES ($1, $2, $3)",
      [userId, imageUrl, caption]
    );

    res.status(201).json({ message: "Post criado com sucesso" });
  } catch (err) {
    console.error("Erro ao salvar post:", err);
    res.status(500).json({ message: "Erro no servidor" });
  }
};
exports.getFeed = async (req, res) => {
  try {
    const result = await pool.query(`
      SELECT 
        posts.id,
        posts.image_url,
        posts.caption,
        posts.created_at,
        users.id AS user_id,
        users.name
      FROM posts
      JOIN users ON users.id = posts.user_id
      ORDER BY posts.created_at DESC
    `);

    const protocol = req.protocol;
const host = req.get("host");

const feed = result.rows.map(post => {
  let fileName = post.image_url.replace(/^.*[\\\/]/, '');
  return { 
    ...post, 
    image_url: `${protocol}://${host}/uploads/${fileName}` 
  };
});

    res.json(feed);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: "Erro ao carregar o feed" });
  }
};