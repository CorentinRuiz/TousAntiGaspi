const initializeApp = require('firebase/app');
const { getFirestore, collection, getDocs } = require('firebase/firestore/lite');

// Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyBCDVnA4mFZeSzs-2l02BnS2SW5QyUEFN8",
    authDomain: "tousantigaspi-06.firebaseapp.com",
    projectId: "tousantigaspi-06",
    storageBucket: "tousantigaspi-06.appspot.com",
    messagingSenderId: "672655933460",
    appId: "1:672655933460:web:77b7655c06cad5d4296701",
    measurementId: "G-WZQSDLXGMR"
};

// Initialize Firebase
const firebaseApp = initializeApp.initializeApp(firebaseConfig);
const db = getFirestore(firebaseApp);

module.exports = db;
