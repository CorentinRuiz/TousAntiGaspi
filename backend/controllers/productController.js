const Product= require('../models/productModel');
const Frigo = require('../models/frigoModel');
const moment = require('moment');

exports.getProducts = (req, res) => {
    console.log(req.body);
    Frigo.findOne({_id: req.body.id})
    .then(frigos => {
        console.log(frigos.products);
        Product.find({
            '_id': { $in: frigos.products }
        }).then(products =>{
            console.log(products);
            return res.send(products);
        })
    })
};

exports.addProduct  = (req, res) => {
    console.log(req.body);

    const product = new Product({
        name: req.body.name,
        date: moment(req.body.date, 'DD/MM/YYYY').toDate(),
        quantity: req.body.quantity
    });

    product.save()
    .then(product =>{
        Frigo.updateOne({_id: req.body.id},{
                $push:{
                    products: product._id
                }
            })
        .then(valid =>{
        if(valid) {
            console.log(valid);
            res.sendStatus(200);
        }
        else res.sendStatus(401);
        });
    });
}

exports.deleteProduct  = (req, res) => {
    console.log(req.body);
    Frigo.deleteOne({_id: req.body.id}
    ).then(valid =>{
        if(valid) res.sendStatus(200);
        else res.sendStatus(401);
    })
};
