const Frigo = require('../models/userModel');

exports.getProducts = (req, res) => {
    console.log(req.body);
    Frigo.find({username: req.body.username}).select("frigo -_id").find()
        .then(frigo => {
            console.log(frigo);
            return res.send(frigo);
        })
        .catch(error => res.status(500).json({error}));
};

exports.addProduct  = (req, res) => {
    console.log(req.body);
    Frigo.find({username: req.body.username})
    .select("frigo -_id")
    .find({name: req.body.frigoName})
    .update({
        $push:{
            products: {
                name: req.body.name,
                quantity: req.body.quantity,
                date: req.body.date
            }
        }
    }
    ).then(valid =>{
        if(valid) {
            console.log(valid);
            res.sendStatus(200);
        }
        else res.sendStatus(401);
    })
};
