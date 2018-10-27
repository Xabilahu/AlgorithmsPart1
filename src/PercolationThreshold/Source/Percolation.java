package PercolationThreshold.Source;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF wquf;
    private WeightedQuickUnionUF wqufCheckFull;
    private boolean[][] openSitesGrid;
    private int gridLength;
    private int topConnexion; //virtual top site
    private int bottomConnexion; //virtual bottom site
    private int openSites; //actual number of open sites

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        else {
            this.gridLength = n;
            this.openSitesGrid = new boolean[n][n];
            this.topConnexion = n*n;
            this.bottomConnexion = this.topConnexion + 1;
            this.wquf = new WeightedQuickUnionUF(this.gridLength * this.gridLength + 2);
            this.wqufCheckFull = new WeightedQuickUnionUF(this.gridLength * this.gridLength + 1);

            for (int i = 0; i < this.gridLength; i++){
                this.wquf.union(this.topConnexion, i);
                this.wqufCheckFull.union(this.topConnexion, i);
                this.wquf.union(this.bottomConnexion, ((this.gridLength * this.gridLength) - this.gridLength) + i);
            }
        }
    }

    //tells if row and col are in scope
    private boolean validateParameters (int row, int col) {
        if (row < 1 || row > this.gridLength || col < 1 || col > this.gridLength) return false;
        else return true;
    }

    //map 2D array to 1D array
    private int map(int row, int col){
        return (((row - 1) * this.gridLength) + (col - 1));
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!this.validateParameters(row, col)) throw new IllegalArgumentException();
        else {
            int row2D = row - 1;
            int col2D = col - 1;
            if (!this.openSitesGrid[row2D][col2D]){
                this.openSites++;
                this.openSitesGrid[row2D][col2D] = true;
                int mappedIndex = this.map(row, col);
                //check under
                this.checkAndUnion(row, col,row + 1, col);
                //check above
                this.checkAndUnion(row, col, row2D, col);
                //check right
                this.checkAndUnion(row, col, row, col + 1);
                //check left
                this.checkAndUnion(row, col, row, col2D);
            }
        }
    }

    private void checkAndUnion(int row, int col, int rowToCheck, int colToCheck) {
        if (this.validateParameters(rowToCheck, colToCheck) && this.openSitesGrid[rowToCheck - 1][colToCheck - 1]) {
            int mappedIndex = this.map(row, col);
            this.wquf.union(mappedIndex, this.map(rowToCheck, colToCheck));
            this.wqufCheckFull.union(mappedIndex, this.map(rowToCheck, colToCheck));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!this.validateParameters(row, col)) throw new IllegalArgumentException();
        else return this.openSitesGrid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!this.validateParameters(row, col)) throw new IllegalArgumentException();
        else if (this.isOpen(row, col)) return this.wqufCheckFull.connected(this.topConnexion, this.map(row, col));
        else return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.wquf.connected(this.topConnexion, this.bottomConnexion);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1,2);
        p.open(2,2);
        System.out.println(p.isFull(2, 2));
        System.out.println(p.percolates());
        p.open(3,2);
        System.out.println(p.percolates());
    }



}