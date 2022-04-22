const mongoose = require('mongoose');
const uniqueValidator = require('mongoose-unique-validator');

const userSchema = mongoose.Schema({
    username: {type: String, required: true, unique: true},
    name: {type: String, required: false},
    email: {type: String, required: true, unique: true},
    password: {type: String, required: true},
    frigo: []
});

userSchema.plugin(uniqueValidator);

module.exports = mongoose.model('User', userSchema);
