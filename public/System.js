import { Planet } from "./Planet.js";
import { galacticObject } from "./galactivObject.js";
export class System extends galacticObject {
    constructor() {
        super();
        this.planets = new Array();
        this.generateName();
        this.numberOfPlanets = (this.theSeed.a & 7) + 1; // How Many Planets shall we have max 10.
        for (let i = 0; i < this.numberOfPlanets; i++)
            this.planets.push(new Planet(this.theSeed));
    }
    generateSystemData() {
        throw new Error("Method not implemented.");
    }
    //draw the system on a canvas context using the x and y as centre coordinates
    drawSystem(ctx, x, y) {
        ctx.fillStyle = "gold";
        ctx.fillText("Solar System:" + this.theName +
            "\t Planets:" + this.numberOfPlanets, x, y + 20);
        ctx.fillStyle = "silver";
        for (let i = 0; i < this.numberOfPlanets; i++)
            ctx.fillText("Name:" + this.planets[i].theName.toLocaleLowerCase()
                + "\t moons:" + this.planets[i].numberOfMoons, x + 40, y + 40 + 20 * i);
    }
}
