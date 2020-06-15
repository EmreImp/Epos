import { Planet } from "./Planet.js";
import { galacticObject } from "./galactivObject.js";
export class System extends galacticObject {
    constructor(aSeed) {
        super(aSeed);
        this.planets = new Array();
        this.numberOfPlanets = (this.theSeed[2] >> 3) & 7; // How Many Planets shall we have max 10.
        this.x = this.theSeed[1] >> 6;
        this.y = this.theSeed[0] >> 6;
        console.log("x:" + this.x + " y:" + this.y);
        this.generateName();
        for (let i = 0; i < this.numberOfPlanets; i++) {
            let planet = new Planet(this.theSeed, this.theName, i + 1);
            this.planets.push(planet);
        }
    }
    //draw the system on a canvas context using the x and y as centre coordinates
    drawSystem(ctx, x, y) {
        ctx.font = "14px Arial";
        ctx.fillStyle = "gold";
        ctx.fillText("Solar System:" + this.theName +
            "\tposition CX:" + this.x + " CY:" + this.y +
            "\tPlanets:" + this.numberOfPlanets, x, y + 20);
        ctx.fillStyle = "silver";
        for (let i = 0; i < this.numberOfPlanets; i++)
            ctx.fillText("Name:" + this.planets[i].theName
                + "\t moons:" + this.planets[i].numberOfMoons, x + 40, y + 40 + 20 * i);
    }
}
