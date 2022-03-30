const express = require("express");
const router = express.Router();
const firebase = require('../firebase');

router.get("/chemin", function (req, res) {
    // Commande
    res.send(firebase);
});

module.exports = router;
