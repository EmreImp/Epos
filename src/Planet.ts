import { Seed } from "./MersenneTwister.js";
import { galacticObject } from "./galactivObject.js";

export class Planet extends galacticObject {
    numberOfMoons:number
    constructor(aSeed:number[]) {
        super(aSeed)
        this.numberOfMoons = (this.theSeed[0]&3)
    }
}