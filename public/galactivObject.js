//var digrams = "ABOUSEITILETSTONLONUTHNOALLEXEGEZACEBISOUSESARMAINDIREA?ERATENBERALAVETIEDORQUANTEISRION";
export class galacticObject {
    constructor(aSeed) {
        this.twister = 0;
        this.theName = "";
        this.theSeed = [0, 0, 0];
        this.pairs = "..LEXEGEZACEBISOUSESARMAINDIREA.ERATENBERALAVETIEDORQUANTEISRION"; /* Dots should be nullprint characters */
        this.theSeed = aSeed;
    }
    tweakseed() {
        let temp = (this.theSeed[0] + this.theSeed[1] + this.theSeed[2]); /* 2 byte aritmetic */
        this.theSeed[0] = this.theSeed[1];
        this.theSeed[1] = this.theSeed[2];
        this.theSeed[2] = 0xFFFF & temp;
    }
}
