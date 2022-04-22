const express = require("express");
const router = express.Router();

const productController = require('../controllers/productController.js');

router.get('/get', productController.getProducts);
router.post('/add', productController.addProduct);
router.delete('/delete', productController.deleteProduct);

module.exports = router;