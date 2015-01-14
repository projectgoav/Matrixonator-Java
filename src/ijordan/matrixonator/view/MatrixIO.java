package ijordan.matrixonator.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import ijordan.matrixonator.model.Matrix;
import ijordan.matrixonator.model.RREFMatrix;

/* MatrixIO
 * --------
 * Provides static methods to deal with load and saving of matrices
 * 
 * Author: Ewan McCartney
 */
public class MatrixIO {

	//Flag if there has been an error when creating directories
	private static boolean dontSave = false;
	
	/**
	 * Called from startup check to stop IO operations if no directory structure
	 */
	public static void setSaveFlag() { dontSave = true; }
	
	/*
	 * RESET METHOD FROM TESTS. DO NOT CALL FROM APPLICATION
	 */
	public static void resetSaveFlag() { dontSave = false; }
	
	//Load method to load any Matrix
	private static Matrix load(File matrixFile) throws Exception
	{
		// Properties from file
				String name = "";
				LocalDate date = null;
				int Rows = 0;
				int Cols = 0;
				double[][] matrixData = null;

				try {
					// Attempting to read in file given
					FileReader fr = new FileReader(matrixFile);
					BufferedReader br = new BufferedReader(fr);

					name = br.readLine();
					date = LocalDate.parse(br.readLine());
					String[] NumRowsCols = br.readLine().split(",");
					Rows = Integer.parseInt(NumRowsCols[0]);
					Cols = Integer.parseInt(NumRowsCols[1]);

					matrixData = new double[Rows][Cols];

					for (int i = 0; i < Rows; ++i) {
						String row = br.readLine();
						String[] Values = row.split(",");
						int Col = 0;

						for (String val : Values) {
							matrixData[i][Col] = Double.parseDouble(val);
							++Col;
						}
					}

					br.close();
				} catch (Exception e) {
					throw e;
				}

				return new Matrix(name, matrixData, date);
	}
	
	
	
	/**
	 * Load Matrix from File
	 * 
	 * @param filename of Matrix to load. To load RREF, use different call
	 * @return Loaded Matrix
	 * @throws FileNotFoundException, IOException, MatrixonatorIOException
	 */
	public static Matrix loadMatrix(String filename) throws Exception {

		if (dontSave) { throw new MatrixonatorIOException("Save is currently disabled due to Matrixonator not having working directories. Please contact system administor for directory create rights."); } //Checking incase the working directories haven't worked properly
		
		filename = getWorkingDir() + MatrixDir + "/" + filename;

		File matrixFile = new File(filename);

		if (!matrixFile.exists()) {
			throw new FileNotFoundException();
		}

		//Load Matrix and return it
		try { return load(matrixFile); } 
		catch (Exception e) { throw e; }
	}
	
	/**
	 * Loads a RREF Matrix
	 * @param filename, RREF file to load. 
	 * @return Matrix from file
	 * @throws FileNotFoundException, IOException, MatrixonatorIOException
	 */
	public static RREFMatrix loadRREFMatrix(String filename) throws Exception {

		if (dontSave) { throw new MatrixonatorIOException("Save is currently disabled due to Matrixonator not having working directories. Please contact system administor for directory create rights."); } //Checking incase the working directories haven't worked properly
		
		if (!filename.contains("RREF")) { throw new MatrixonatorIOException("Invalid RREF Matrix file given. If loading a non-RREF Matrix, use loadMatrix()"); }
		
		filename = getWorkingDir() + MatrixDir + "/" + filename;

		File matrixFile = new File(filename);

		if (!matrixFile.exists()) {
			throw new FileNotFoundException();
		}

		//Load RREF matrix, format filename and then return RREF matrix
		try { 
			Matrix data =  load(matrixFile); 
			data.setName(data.getName().replace("RREF",""));
			return new RREFMatrix(data);
		} 
		catch (Exception e) { throw e; }
	}

	/**
	 * Save Matrix to file
	 * 
	 * @param matrix
	 * @return True on success, false otherwise
	 */

	/*
	 * Saves Matrix as plain text (for now)
	 * 
	 * File Format: - Matrix Name - Matrix Date - Matrix NumRows/Cols - Matrix
	 * Data (Row per line, Cols split with ,)
	 */

	public static boolean save(Matrix matrix) {
		
		if (dontSave) { return false; }	//Checking for Save flag on startup
		
		//Size required for data
		String[] buffer = new String[(matrix.getNumRows() + 3)]; 
		
		// Adds title information
		buffer[0] = matrix.getName();
		buffer[1] = matrix.getCreatedDate().toString();
		buffer[2] = matrix.getNumRows() + "," + matrix.getNumCols();

		for (int i = 3; i < (matrix.getNumRows() + 3); ++i) {
			// For each row, we add a new line and put each value in a string
			// seperated by ,
			StringBuilder line = new StringBuilder();
			double[] row = matrix.getRow(i - 3);

			for (double val : row) {
				line.append(val);
				line.append(",");
			}

			buffer[i] = line.toString();
		}

		// Actual IO Operation in try
		try {
			// "./" means to save in the local application directory
			File matrixFile = new File(getWorkingDir() + MatrixDir + "/" + buffer[0] + ".matrix");
			
			if (!matrixFile.exists()) {
				matrixFile.createNewFile();
			}

			FileWriter fw = new FileWriter(matrixFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			for (String line : buffer) {
				bw.append(line + "\n");
			}

			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * IO Helper Methods
	 */
	private static final String MatrixDir = "/Matrixonator/Matrix";
	private static final String LocalDir = "/Matrixonator";

	/**
	 * @return The path to application working directory
	 */
	private static String getWorkingDir() {
		return System.getProperty("user.dir");
	}

	// Checks both directories are there and attempts to make them. Throw the
	// non-critical exception
	public static void checkDirectories() throws MatrixonatorIOException {
		File BaseDirectory = new File(getWorkingDir() + LocalDir);
		if (!BaseDirectory.exists()) {

			try {
				if (!BaseDirectory.mkdir()) {
					throw new MatrixonatorIOException("Unable to create working directories. Please contact system administrator for directory create rights.\n\nMatrixonator will still work, however you will be unable to save matrices or preferences");
				}
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new MatrixonatorIOException("Unable to create working directories due to secuirty issues. Please contact system administrator for directory create rights.");
			}
		}

		File MatrixDirectory = new File(getWorkingDir() + MatrixDir);
		if (!MatrixDirectory.exists()) {

			try {
				if (!MatrixDirectory.mkdir()) {
					throw new MatrixonatorIOException("Unable to create working directories. Please contact system administrator for directory create rights.\n\nMatrixonator will still work, however you will be unable to save matrices or preferences");
				}
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new MatrixonatorIOException("Unable to create working directories due to secuirty issues. Please contact system administrator for directory create rights.");
			}
		}
	}

}