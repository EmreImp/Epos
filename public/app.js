import { System } from "./System.js";
var canvas = document.getElementById("gameScreen");
var ctx = canvas.getContext("2d");
let sol = new System();
sol.drawSystem(ctx, 10, 10);
console.log(sol);
