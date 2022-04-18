const mongoose = require('mongoose');
const uniqueValidator = require('mongoose-unique-validator');

const productSchema = mongoose.Schema({
    name: {type: String, required: true},
    quantity: {type: Number, required: true},
    date: {type: Date, require: true}
});

productSchema.plugin(uniqueValidator);

module.exports = mongoose.model('Product', productSchema);