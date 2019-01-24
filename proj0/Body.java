public class Body{
  public double xxPos, yyPos, xxVel, yyVel, mass;
  public String imgFileName;

  /** Planet constructors */
  public Body(double xP, double yP, double xV, double yV, double m, String img){
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }

  public Body(Body p){
    xxPos = p.xxPos;
    yyPos = p.yyPos;
    xxVel = p.xxVel;
    yyVel = p.yyVel;
    mass = p.mass;
    imgFileName = p.imgFileName;
  }

  public double calcDistance(Body p){
    double dxs = (xxPos - p.xxPos) * (xxPos - p.xxPos);
    double dys = (yyPos - p.yyPos) * (yyPos - p.yyPos);
    return Math.pow(dxs + dys, .5);
  }

  public double calcForceExertedBy(Body p){
    double g = 6.67e-11;
    double rs = calcDistance(p) * calcDistance(p);
    return (g * mass * p.mass)/rs;
  }

  public double calcForceExertedByX(Body p){
    double tforce = calcForceExertedBy(p);
    double distance = p.xxPos - xxPos;
    double radius = calcDistance(p);
    return (tforce * distance) / radius;
  }

  public double calcForceExertedByY(Body p){
    double tforce = calcForceExertedBy(p);
    double distance = p.yyPos - yyPos;
    double radius = calcDistance(p);
    return (tforce * distance) / radius;
  }

  public double calcNetForceExertedByX(Body[] p){
    double xforce = 0;
    for(int i = 0; i < p.length; i++){
      if(this.equals(p[i])){
        xforce = xforce + 0;
      }else{
        xforce = xforce + this.calcForceExertedByX(p[i]);
      }
    }
    return xforce;
  }

  public double calcNetForceExertedByY(Body[] p){
    double yforce = 0;
    for(int i = 0; i < p.length; i++){
      if(this.equals(p[i])){
        yforce = yforce + 0;
      }else{
        yforce = yforce + this.calcForceExertedByY(p[i]);
      }
    }
    return yforce;
  }

  public void update(double time, double xxForce, double yyForce){
    double ax = xxForce / mass;
    double ay = yyForce / mass;
    xxVel = xxVel + time * ax;
    yyVel = yyVel + time * ay;
    xxPos = xxPos + time * xxVel;
    yyPos = yyPos + time * yyVel;
  }

  public void draw(){
    StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
  }
}
