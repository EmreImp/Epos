import { galacticObject } from "./galactivObject.js";
export class Planet extends galacticObject {
    constructor(aSeed) {
        super(aSeed);
        this.generateName();
        this.numberOfMoons = (this.theSeed.a & 3);
    }
}
