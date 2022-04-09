const express = require("express");
const router = express.Router();

const userController = require('../controllers/userController.js');

// List of routes allowing access to functions, based on a URL
router.get('/register', userController.register);
router.get('/login', userController.login);

module.exports = router;
