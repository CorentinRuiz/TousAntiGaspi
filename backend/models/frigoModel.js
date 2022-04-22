const mongoose = require('mongoose');
const Product = require('./productModel').schema;
const uniqueValidator = require('mongoose-unique-validator');

const frigoSchema = mongoose.Schema({
    name: {type: String, required: true},
    products: []
});

frigoSchema.plugin(uniqueValidator);

module.exports = mongoose.model('Frigo', frigoSchema);

