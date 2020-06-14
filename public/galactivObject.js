import { Seed } from "./MersenneTwister.js";
import { MersenneTwister } from "./MersenneTwister.js";
let mysticNumber = Math.random() * 10 ^ 12;
var digrams = "ABOUSEITILETSTONLONUTHNOALLEXEGEZACEBISOUSESARMAINDIREA?ERATENBERALAVETIEDORQUANTEISRION";
export class galacticObject {
    constructor(aSeed) {
        this.twister = 0;
        this.theName = "";
        if (!aSeed) {
            this.twister = new MersenneTwister(mysticNumber).genrand_int32();
            this.theSeed = new Seed(this.twister);
        }
        else
            this.theSeed = aSeed;
    }
    generateName() {
        var size;
        var i;
        var gp;
        var x;
        if ((this.theSeed.a & 64) == 0)
            size = 3;
        else
            size = 4;
        for (i = 0; i < size; i++) {
            x = this.theSeed.f & 31;
            if (x != 0) {
                x += 12;
                x *= 2;
                this.theName += digrams[x];
                if (digrams[x + 1] != '?')
                    this.theName += digrams[x + 1];
            }
            this.waggle_galaxy();
        }
    }
    waggle_galaxy() {
        var x;
        var y;
        var carry_flag;
        x = this.theSeed.a + this.theSeed.c;
        y = this.theSeed.b + this.theSeed.d;
        if (x > 0xFF)
            y++;
        x &= 0xFF;
        y &= 0xFF;
        this.theSeed.a = this.theSeed.c;
        this.theSeed.b = this.theSeed.d;
        this.theSeed.c = this.theSeed.e;
        this.theSeed.d = this.theSeed.f;
        x += this.theSeed.c;
        y += this.theSeed.d;
        if (x > 0xFF)
            y++;
        if (y > 0xFF)
            carry_flag = 1;
        else
            carry_flag = 0;
        x &= 0xFF;
        y &= 0xFF;
        this.theSeed.e = x;
        this.theSeed.f = y;
    }
}
