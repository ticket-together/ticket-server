importScripts('https://www.gstatic.com/firebasejs/9.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/9.10.0/firebase-analytics.js')

let firebaseConfig = {
    apiKey: "AIzaSyCaq1GgBwMbm60e0Ezm2U6RkAmb67GklMQ",
    authDomain: "ticket-together.firebaseapp.com",
    projectId: "ticket-together",
    storageBucket: "ticket-together.appspot.com",
    messagingSenderId: "520037783133",
    appId: "1:520037783133:web:a41f455d736b2c4a6a9fb4",
    measurementId: "G-NCCZTKWF2F"
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();