import { Seed } from "./MersenneTwister.js";
import { galacticObject } from "./galactivObject.js";

export class Planet extends galacticObject {
    numberOfMoons:number
    constructor(aSeed:Seed) {
        super(aSeed)
        this.generateName()
        this.numberOfMoons = (this.theSeed.a&3)
    }
}