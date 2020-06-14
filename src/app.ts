import { System } from "./System.js";

var canvas=<HTMLCanvasElement>document.getElementById("gameScreen")
var ctx = <CanvasRenderingContext2D>canvas.getContext("2d");

let sol=new System()
sol.drawSystem(ctx, 10, 10)
console.log(sol)