//var digrams = "ABOUSEITILETSTONLONUTHNOALLEXEGEZACEBISOUSESARMAINDIREA?ERATENBERALAVETIEDORQUANTEISRION";

export class galacticObject {
    twister:number=0;
    theName:string=""
    theSeed:number[]=[0,0,0]
    pairs = "..LEXEGEZACEBISOUSESARMAINDIREA.ERATENBERALAVETIEDORQUANTEISRION"; /* Dots should be nullprint characters */


    constructor (aSeed:number[]) {
        this.theSeed=aSeed
    }

    tweakseed()
	{ 
		let temp = (this.theSeed[0]+this.theSeed[1]+this.theSeed[2]); /* 2 byte aritmetic */
		this.theSeed[0] = this.theSeed[1];
		this.theSeed[1] = this.theSeed[2];
		this.theSeed[2] = 0xFFFF&temp;
	}

}