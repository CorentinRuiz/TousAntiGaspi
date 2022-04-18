const express = require('express');
const mongoose = require('mongoose');
const userRoutes = require('./routes/userRoutes');
const frigoRoutes = require('./routes/frigoRoutes');
const productRoutes = require('./routes/productRoutes');

mongoose.connect('mongodb+srv://android_app:O10d0sV8KDrHb6cJ@tousantigaspi.yt9ta.mongodb.net/tousAntiGaspi?retryWrites=true&w=majority',
    { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log('Connexion à MongoDB réussie !'))
    .catch(() => console.log('Connexion à MongoDB échouée !'));

const app = express();

app.use(express.json());

app.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content, Accept, Content-Type, Authorization');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS');
    next();
});

app.use('/user', userRoutes);
app.use('/frigo',frigoRoutes);
app.use('/product',productRoutes);

module.exports = app;
