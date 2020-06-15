import { System } from "./System.js";
var canvas = document.getElementById("gameScreen");
var ctx = canvas.getContext("2d");
let seed = [0, 0, 0];
let galsize = 256;
let galaxy = new Array();
buildgalaxy(1);
function buildgalaxy(galaxynum) {
    var syscount, galcount;
    //And what about the other galaxies?
    let base0 = 0x5A4A;
    let base1 = 0x0248;
    let base2 = 0xB753; /* Base seed for galaxy 1 */
    seed[0] = base0;
    seed[1] = base1;
    seed[2] = base2; /* Initialise seed for galaxy 1 */
    for (galcount = 1; galcount < galaxynum; ++galcount)
        nextgalaxy();
    /* Put galaxy data into array of structures */
    for (syscount = 0; syscount < galsize; ++syscount) {
        let system = new System(seed);
        console.log(system);
        galaxy[syscount] = system;
    }
}
function rotatel(x) {
    return (2 * (x & 127)) + (x >> 7);
}
function twist(x) {
    return ((256 * rotatel(x >> 8)) + rotatel(x & 255));
}
function nextgalaxy() {
    seed[0] = twist(seed[0]); /* twice for galaxy 3, etc. */
    seed[1] = twist(seed[1]); /* Eighth application gives galaxy 1 again*/
    seed[2] = twist(seed[2]);
}
