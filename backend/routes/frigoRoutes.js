const express = require("express");
const router = express.Router();

const frigoController = require('../controllers/frigoController.js');

router.post('/get', frigoController.getFrigo);
router.post('/create', frigoController.createFrigo);
router.put('/edit',frigoController.editFrigo);
router.post('/delete',frigoController.deleteFrigo);

module.exports = router;