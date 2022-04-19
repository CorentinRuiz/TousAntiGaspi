const express = require("express");
const router = express.Router();

const frigoController = require('../controllers/frigoController.js');

router.post('/get', frigoController.getFrigo);
router.post('/create', frigoController.createFrigo);
router.put('/edit',frigoController.editFrigo);

module.exports = router;