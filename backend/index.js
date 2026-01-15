const express = require('express');
const app = express();

app.use(express.json());

app.get('/teste', (req, res) => {
  res.send('API rodando 🚀');
});

app.listen(3000, () => {
  console.log('Servidor na porta 3000');
});
