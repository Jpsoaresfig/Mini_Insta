const { Pool } = require("pg");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

const pool = new Pool({
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  database: process.env.DB_NAME,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
});

// LOGIN
exports.login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const result = await pool.query("SELECT * FROM users WHERE email = $1", [email]);
    const user = result.rows[0];

    if (!user) return res.status(401).json({ message: "Usuário não encontrado" });

    const valid = await bcrypt.compare(password, user.password);
    if (!valid) return res.status(401).json({ message: "Senha inválida" });

    const token = jwt.sign({ id: user.id }, process.env.JWT_SECRET, { expiresIn: "7d" });

    res.json({ token });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: "Erro no servidor" });
  }
};

// REGISTER
exports.register = async (req, res) => {
  const { email, password } = req.body;

  try {
    // Verifica se já existe usuário
    const exists = await pool.query("SELECT * FROM users WHERE email = $1", [email]);
    if (exists.rows.length > 0) {
      return res.status(400).json({ message: "Usuário já existe" });
    }

    // Criptografa a senha
    const hashed = await bcrypt.hash(password, 10);

    // Insere no banco
    const result = await pool.query(
      "INSERT INTO users (email, password) VALUES ($1, $2) RETURNING *",
      [email, hashed]
    );
    const user = result.rows[0];

    // Gera token
    const token = jwt.sign({ id: user.id }, process.env.JWT_SECRET, { expiresIn: "7d" });

    res.status(201).json({ token });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: "Erro no servidor" });
  }
};
