const User = require('../models/userModel');

exports.getFrigo = (req, res) => {
    console.log(req.body);
    User.findOne({username: req.body.username}).select('frigo -_id')
        .then(frigos => {
            console.log(frigos);
            return res.send(frigos);
        })
        .catch(error => res.status(500).json({error}));
};

exports.createFrigo  = (req, res) => {
    console.log(req.body);
    User.update({username: req.body.username,}, {
        $push:{
            frigo: {
                name: req.body.name,
                product: []
            }
        }
    }
    ).then(valid =>{
        if(valid) res.sendStatus(200);
        else res.sendStatus(401);
    })
};