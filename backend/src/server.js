require("dotenv").config();
const app = require("./app");
const pool = require("./config/db");

app.listen(3000, () => {
  console.log("Servidor rodando na porta 3000");
});
