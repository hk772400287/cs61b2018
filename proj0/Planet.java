public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static double G = 6.67e-11;

	public Planet(double xP, double yP, double xV,
              	  double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		double r;
		r = Math.sqrt(dx * dx + dy * dy);
		return r;
	}

	public double calcForceExertedBy(Planet p) {
		double r = this.calcDistance(p);
		return G * this.mass * p.mass / (r * r);
	}

	public double calcForceExertedByX(Planet p) {
		double F = calcForceExertedBy(p);
		double r = this.calcDistance(p);
		double dx = p.xxPos - this.xxPos;
		return F * dx / r;
	}

	public double calcForceExertedByY(Planet p) {
		double F = calcForceExertedBy(p);
		double r = this.calcDistance(p);
		double dy = p.yyPos - this.yyPos;
		return F * dy / r;
	}

	public double calcNetForceExertedByX(Planet[] ps) {
		double sum = 0;
		for (Planet p : ps) {
			if (p.equals(this)) {
				continue;
			}
			sum += this.calcForceExertedByX(p);
		}
		return sum;
	}

	public double calcNetForceExertedByY(Planet[] ps) {
		double sum = 0;
		for (Planet p : ps) {
			if (p.equals(this)) {
				continue;
			}
			sum += this.calcForceExertedByY(p);
		}
		return sum;
	}

	public void update(double dt, double fX, double fY) {
		double ax = fX / this.mass;
		double ay = fY / this.mass;
		this.xxVel += dt * ax;
		this.yyVel += dt * ay;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}
