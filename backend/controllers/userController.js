const User = require('../models/userModel');
const bcrypt = require('bcrypt');

exports.register = (req, res) => {
    bcrypt.hash(req.body.password, 10)
        .then(hashedPassword => {
            const user = new User({
                username: req.body.username,
                email: req.body.email,
                password: hashedPassword
            });

            user.save()
                .then(() => res.sendStatus(201))
                .catch(error => res.status(400).json({error}));
        })
};

exports.login = (req, res) => {
    console.log(req.body);
    User.findOne({username: req.body.username})
        .then(user => {
            if(!user) res.status(404).json({error: "User not found"});

            bcrypt.compare(req.body.password, user.password)
                .then(valid => {
                    if(valid) res.sendStatus(200);
                    else res.sendStatus(401);
                });
        })
        .catch(error => res.status(500).json({error}));
}

exports.addName  = (req, res) => {
    console.log(req.body);
    if(req.body.name === ""){
        res.sendStatus(400);
    }

    User.updateOne({username: req.body.username}, {
        $set:{
            name: req.body.name
        }
    }
    ).then(valid =>{
        if(valid) res.sendStatus(200);
        else res.sendStatus(401);
    })
};

exports.getName  = (req, res) => {
    console.log(req.body);
    User.findOne({username: req.body.username}).select('name -_id')
        .then(name => {
            console.log(name);
            return res.send(name);
        })
        .catch(error => res.status(500).json({error}));
};
