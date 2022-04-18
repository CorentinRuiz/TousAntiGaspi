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
