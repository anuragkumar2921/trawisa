
const express = require('express');
const app = express();
const PORT = 8080;

app.get('/', (req, res) => {
    res.send('Hello, World!');
});

app.get('/hey', (req, res) => {
    res.send('Hello, World! - hey');
});

app.post('/hey', (req, res) => {
    res.send('Hello, World! - hey');
});

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});