let pairs = "..LEXEGEZACEBISOUSESARMAINDIREA.ERATENBERALAVETIEDORQUANTEISRION"; /* Dots should be nullprint characters */
let pairs2 = "ABOUSEITILETSTONLONUTHNOAL.LEXEGEZACEBISOUSESARMAINDIREA.ERATENBERALAVETIEDORQUANTEISRION";

const int2roman = (original: number): string => {
    if (original < 1 || original > 3999) {
      throw new Error('Error: Input integer limited to 1 through 3,999');
    }
  
    const numerals = [
      ['I', 'II', 'III', 'IV', 'V', 'VI', 'VII', 'VIII', 'IX'], // 1-9
      ['X', 'XX', 'XXX', 'XL', 'L', 'LX', 'LXX', 'LXXX', 'XC'], // 10-90
      ['C', 'CC', 'CCC', 'CD', 'D', 'DC', 'DCC', 'DCCC', 'CM'], // 100-900
      ['M', 'MM', 'MMM'], // 1000-3000
    ];
  
    // TODO: Could expand to support fractions, simply rounding for now
    const digits = Math.round(original).toString().split('');
    let position = (digits.length - 1);
  
    return digits.reduce((roman, digit) => {
      if (digit !== '0') {
        roman += numerals[position][parseInt(digit) - 1];
      }
  
      position -= 1;
  
      return roman;
    }, '');
  }

export class galacticObject {
    twister:number=0;
    theName:string=""
    theSeed:number[]=[0,0,0]
    goatsoupseed:number[]=[0,0,0,0]


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
    
    generateName(Name?:string, ID?:number) {
        let longnameflag=this.theSeed[0]&64;
        this.goatsoupseed[0] = this.theSeed[1] & 0xFF;
		this.goatsoupseed[1] = this.theSeed[1] >>8;
		this.goatsoupseed[2] = this.theSeed[2] & 0xFF;
		this.goatsoupseed[3] = this.theSeed[2] >> 8;
	
		let pair1=2*((this.theSeed[2]>>8)&31);  this.tweakseed();
		let pair2=2*((this.theSeed[2]>>8)&31);  this.tweakseed();
		let pair3=2*((this.theSeed[2]>>8)&31);  this.tweakseed();
        let pair4=2*((this.theSeed[2]>>8)&31);	this.tweakseed();
        var name:string=""

        if(ID) {
            let namingConvention = ((this.theSeed[2]>>3)&7)
            if(namingConvention<4) { 
                this.theName=Name+" "+ID
                return
            }
            else if(namingConvention<8) {
                this.theName=Name+" "+ int2roman(ID) 
                return
            }
        }
        //generate a new one
        if(pairs[pair1]!='.') name+=pairs[pair1];
		if(pairs[pair1+1]!='.') name+=pairs[pair1+1];
		if(pairs[pair2]!='.') name+=pairs[pair2];
		if(pairs[pair2+1]!='.') name+=pairs[pair2+1];
		if(pairs[pair3]!='.') name+=pairs[pair3];
		if(pairs[pair3+1]!='.') name+=pairs[pair3+1];
		if(longnameflag>0) /* bit 6 of ORIGINAL w0 flags a four-pair name */
		{
			if(pairs[pair4]!='.') name+=pairs[pair4];
			if(pairs[pair4+1]!='.') name+=pairs[pair4+1];
		}
		this.theName=name;
    }

}