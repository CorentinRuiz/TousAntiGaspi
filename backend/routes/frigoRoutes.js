const express = require("express");
const router = express.Router();

const frigoController = require('../controllers/frigoController.js');

router.get('/get', frigoController.getFrigo);
router.post('/create', frigoController.createFrigo);

module.exports = router;