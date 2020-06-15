import { galacticObject } from "./galactivObject.js";
export class Planet extends galacticObject {
    constructor(aSeed) {
        super(aSeed);
        this.numberOfMoons = (this.theSeed[0] & 3);
    }
}
