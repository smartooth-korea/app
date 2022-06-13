/**
 * PBKDF2 방식의 AES128 암호화
 */
var opEncrypt = function (plainText) {
    var PASS_SALT = "d9976d879b0ddde5f9cb7d76b24cc9a1";
    var PASS_IV = "af9aa276320bc35e22c4497ea49c2568";
    var PASS_PHRASE = "smartooth";
    var PASS_ITERATION = 1000;
    var PASS_KEY_SIZE = 128;

    // PBKDF2 키 생성
    var key128Bits100Iterations = CryptoJS.PBKDF2(PASS_PHRASE, CryptoJS.enc.Hex.parse(PASS_SALT), {keySize: parseInt(PASS_KEY_SIZE)/32, iterations: parseInt(PASS_ITERATION)});
    var encrypted = CryptoJS.AES.encrypt(plainText, key128Bits100Iterations, {iv: CryptoJS.enc.Hex.parse(PASS_IV)});

    return encrypted.toString();
};

