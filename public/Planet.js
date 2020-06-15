import { galacticObject } from "./galactivObject.js";
export class Planet extends galacticObject {
    constructor(aSeed, systemName, planetID) {
        super(aSeed);
        this.systemName = systemName;
        this.planetID = planetID;
        this.numberOfMoons = (this.theSeed[0] & 3);
        this.govtype = ((this.theSeed[1] >> 3) & 7); /* bits 3,4 &5 of w1 */
        this.economy = ((this.theSeed[0] >> 8) & 7); /* bits 8,9 &A of w0 */
        if (this.govtype <= 1) {
            this.economy = ((this.economy) | 2);
        }
        this.techlevel = ((this.theSeed[1] >> 8) & 3) + ((this.economy) ^ 7);
        this.techlevel += ((this.govtype) >> 1);
        if (((this.govtype) & 1) == 1)
            this.techlevel += 1;
        /* C simulation of 6502's LSR then ADC */
        this.population = 4 * (this.techlevel) + (this.economy);
        this.population += (this.govtype) + 1;
        this.productivity = (((this.economy) ^ 7) + 3) * ((this.govtype) + 4);
        this.productivity *= (this.population) * 8;
        this.radius = 256 * (((this.theSeed[2] >> 8) & 15) + 11) + 100;
        this.generateName(this.systemName, this.planetID);
        //this.market=new MarketType(0, this);
    }
}
