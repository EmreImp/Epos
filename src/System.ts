import { Planet } from "./Planet.js";
import { galacticObject } from "./galactivObject.js";


export class System extends galacticObject {
    numberOfPlanets:number
    planets:Array<Planet>=new Array<Planet>()
    goatsoupseed:number[]=[0,0,0,0]
    x:number
    y:number


    constructor(aSeed:number[]) {
        super(aSeed)
        this.numberOfPlanets = (this.theSeed[2]>>3)&7 // How Many Planets shall we have max 10.
        let longnameflag=this.theSeed[0]&64;
        this.x=this.theSeed[1]>>8;
        this.y=this.theSeed[0]>>8;
        
        this.goatsoupseed[0] = this.theSeed[1] & 0xFF;
		this.goatsoupseed[1] = this.theSeed[1] >>8;
		this.goatsoupseed[2] = this.theSeed[2] & 0xFF;
		this.goatsoupseed[3] = this.theSeed[2] >> 8;
	
		let pair1=2*((this.theSeed[2]>>8)&31);  this.tweakseed();
		let pair2=2*((this.theSeed[2]>>8)&31);  this.tweakseed();
		let pair3=2*((this.theSeed[2]>>8)&31);  this.tweakseed();
        let pair4=2*((this.theSeed[2]>>8)&31);	this.tweakseed();
        
        var name:string=""

        if(this.pairs[pair1]!='.') name+=this.pairs[pair1];
		if(this.pairs[pair1+1]!='.') name+=this.pairs[pair1+1];
		if(this.pairs[pair2]!='.') name+=this.pairs[pair2];
		if(this.pairs[pair2+1]!='.') name+=this.pairs[pair2+1];
		if(this.pairs[pair3]!='.') name+=this.pairs[pair3];
		if(this.pairs[pair3+1]!='.') name+=this.pairs[pair3+1];
		if(longnameflag>0) /* bit 6 of ORIGINAL w0 flags a four-pair name */
		{
			if(this.pairs[pair4]!='.') name+=this.pairs[pair4];
			if(this.pairs[pair4+1]!='.') name+=this.pairs[pair4+1];
		}
		this.theName=name;
		//this.market=new MarketType(0, thissys);

    }

        //draw the system on a canvas context using the x and y as centre coordinates
    drawSystem (ctx:CanvasRenderingContext2D, x:number, y:number) {
        ctx.fillStyle = "gold"
        ctx.fillText("Solar System:" + this.theName.toLocaleLowerCase() +
            "\tposition CX:"+ this.x + " CY:" + this.y +
            "\tPlanets:" + this.numberOfPlanets, 
            x, y+20)
        ctx.fillStyle = "silver"
        for(let i=0;i<this.numberOfPlanets;i++)
            ctx.fillText("Name:"+ this.planets[i].theName.toLocaleLowerCase()
            +"\t moons:"+this.planets[i].numberOfMoons, 
            x+40, y+40+20*i)
    }

}