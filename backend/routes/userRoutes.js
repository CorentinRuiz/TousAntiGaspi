const express = require("express");
const router = express.Router();

const userController = require('../controllers/userController.js');

// List of routes allowing access to functions, based on a URL
router.post('/register', userController.register);
router.post('/login', userController.login);
router.post('/add/name', userController.addName);
router.post('/get/name', userController.getName);

module.exports = router;
