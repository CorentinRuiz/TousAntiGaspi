const User = require('../models/userModel');
const Frigo = require('../models/frigoModel')

exports.getFrigo = (req, res) => {
    Frigo.findOne({_id: req.body.id})
        .then(frigos => {
            console.log(frigos);
            return res.send(frigos);
        })
        .catch(error => res.status(500).json({error}));
};

exports.createFrigo  = (req, res) => {
    console.log(req.body);

    const frigo = new Frigo({
        name: req.body.name,
        product: []
    });

    frigo.save()
         .then( frigo => {
            User.update({username: req.body.username}, {
                $push:{
                    frigo: frigo._id
                }
            }
            ).then(valid =>{
                if(valid) res.sendStatus(200);
                else res.sendStatus(401);
            })
         })


};

exports.editFrigo  = (req, res) => {
    console.log(req.body);
    Frigo.updateOne({id: req.body.id}, {
        name: req.body.name
    }
    ).then(valid =>{
        if(valid) res.sendStatus(200);
        else res.sendStatus(401);
    })
};

exports.deleteFrigo  = (req, res) => {
    console.log(req.body);
    Frigo.deleteOne({_id: req.body.id}
    ).then(valid =>{
        if(valid) res.sendStatus(200);
        else res.sendStatus(401);
    })
};