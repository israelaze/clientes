/*const express = require('express');
const app = express();

const PORT = process.env.PORT || 8080;

// Serve only the static files form the dist directory
app.use(express.static(__dirname + '/dist/angular-clientes'));

app.get('/*', (req, res) => {
    res.sendFile(__dirname + '/dist/angular-clientes/index.html');
});

// Start the app by listening on the default Heroku port
app.listen(PORT, () => {
    console.log('Servidor iniciado na porta ' + PORT);
})
*/



const express = require('express');
const app = express();

// Serve only the static files form the dist directory
app.use(express.static('./dist/angular-clientes'));

app.get('/*', (req, res) =>
    res.sendFile('index.html', {root: 'dist/angular-clientes'}),
);

// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 8080);


/*
// importar express
const express = require('express');
// iniciar express
const app = express();
// nome da pasta no dist que sera feito o build
const appName = 'angular-clientes';
// local onde build ira gerar os arquivos
const outputPath = `${__dirname}/dist/${angular-clientes}`;

// seta o diretorio de build para servir o conteudo Angular
app.use(express.static(outputPath));
// redirecionar qualquer requisicao para o index.html
app.get('/*', (req, res) => {
  res.sendFile(`${outputPath}/index.html`);
});
// ouvir a porta que o Heroku disponibilizar
app.listen(process.env.PORT || 8080);
*/