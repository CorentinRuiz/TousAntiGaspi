const Product= require('../models/productModel');
const Frigo = require('../models/frigoModel')

exports.getProducts = (req, res) => {
    Frigo.findOne({_id: req.body.id})
    .then(frigos => {
        console.log(frigos.products);
        Product.find({
            '_id': { $in: frigos.products }
        }).then(products =>{
            return res.send(products);
        })
    })
};

exports.addProduct  = (req, res) => {
    console.log(req.body);

    const product = new Product({
        name: req.body.name,
        date: Date(req.body.date),
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
