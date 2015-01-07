package ijordan.matrixonatorTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import ijordan.matrixonator.model.Matrix;

import org.junit.Test;

public class MatrixTest {

	@Test
	public void testMatrixCreation() {
		double[][] data = new double[5][2];
		data[1][1] = 5.2;

		final Matrix testMatrix = new Matrix("Test", data);
		assertEquals("Created matrix has inaccurate number of rows", testMatrix.getNumRows(), 5);
		assertEquals("Created matrix has inaccurate number of columns", testMatrix.getNumCols(), 2);
		assertEquals("Created matrix has inaccurate data", testMatrix.getData(), data);

	}

	@Test
	public void testMatrixMultiplication1() {
		double[][] dataFirst = { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] dataSecond = { { 7, 8 }, { 9, 10 }, { 11, 12 } };

		double[][] dataResult = { { 58, 64 }, { 139, 154 } };

		final Matrix testMatrix1 = new Matrix("Test1", dataFirst);
		final Matrix testMatrix2 = new Matrix("Test2", dataSecond);
		final Matrix testMatrixResult = new Matrix("TestResult", dataResult);

		assertTrue("Matrices should be compatible",
				Matrix.checkMultCompatibility(testMatrix1, testMatrix2));

		assertEquals("Matrix Multiplication gives incorrect result", testMatrixResult.getData(),
				Matrix.multiplyMatrices(testMatrix1, testMatrix2).getData());

	}

	@Test
	public void testMatrixMultiplication2() {
		double[][] dataFirst = { { 1586, 7546, 85746, 57564 }, { 756, 374, 385, 0 },
				{ 8765, 123, 765, 234 } };
		double[][] dataSecond = { { 6543, 7896, 9876 }, { 76546, 765467, 4657 },
				{ 34765, 28452, 9876789 }, { 897655, 876, 5 } };

		double[][] dataResult = { { 55241565424.0, 8278808294.0, 846946242472.0 },
				{ 46959237, 303208054, 3811771739.0 }, { 303411048, 185331645, 7642880706.0 } };

		final Matrix testMatrix1 = new Matrix("Test1", dataFirst);
		final Matrix testMatrix2 = new Matrix("Test2", dataSecond);
		final Matrix testMatrixResult = new Matrix("TestResult", dataResult);

		assertTrue("Matrices should be compatible",
				Matrix.checkMultCompatibility(testMatrix1, testMatrix2));

		assertEquals("Matrix Multiplication gives incorrect result", testMatrixResult.getData(),
				Matrix.multiplyMatrices(testMatrix1, testMatrix2).getData());

	}

	@Test
	public void testMatrixMultCompatibility() {
		double[][] dataFirst = { { 1, 2, 3 }, { 1, 2, 3 }, { 12, 3, 4 } };
		double[][] dataSecond = { { 2, 3 }, { 3, 7 } };

		final Matrix testMatrix1 = new Matrix("Test1", dataFirst);
		final Matrix testMatrix2 = new Matrix("Test2", dataSecond);

		assertFalse("Matrices should not be compatible",
				Matrix.checkMultCompatibility(testMatrix1, testMatrix2));

		assertFalse("Matrices should not be compatible",
				Matrix.checkMultCompatibility(testMatrix2, testMatrix1));

	}

	@Test
	public void testMatrixAdd() {
		double[][] dataFirst = { { 1586, 7546, 85746, 57564 }, { 756, 374, 385, 0 },
				{ 8765, 123, 765, 234 } };
		double[][] dataSecond = { { 6543, 7896, 9876, 156 }, { 76546, 765467, 4657, 573 },
				{ 34765, 28452, 9876789, 475645323234.0 } };

		double[][] dataResult = { { 8129, 15442, 95622, 57720 }, { 77302, 765841, 5042, 573 },
				{ 43530, 28575, 9877554, 475645323468.0 } };

		final Matrix testMatrix1 = new Matrix("Test1", dataFirst);
		final Matrix testMatrix2 = new Matrix("Test2", dataSecond);
		final Matrix testMatrixResult = new Matrix("TestResult", dataResult);

		assertEquals("Matrix Addition gives incorrect result", testMatrixResult.getData(), Matrix
				.addMatrices(testMatrix1, testMatrix2).getData());

	}

	@Test
	public void testMatrixReduceNormal() {
		double[][] data = { { 1, 0, 3, 3 }, { 0, 1, 0, 4 }, { 0, 0, 0, 1 } };
		double[][] dataResult = { { 1, 0, 3, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 1 } };
		final Matrix testMatrix = new Matrix("Test1", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult, testMatrix
				.reducedEchelonForm().getData());
	}
	
	@Test
	public void testMatrixReduceNormal2() {
		double[][] data = { { 15, 3, 6 }, { 0, 3, 6 } };
		double[][] dataResult = { { 1, 0, 0 }, { 0, 1, 2 } };
		final Matrix testMatrix = new Matrix("Test1", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult, testMatrix
				.reducedEchelonForm().getData());
	}
	
	@Test
	public void testMatrixReduceManyRows() {
		double[][] data = {{1, 2, 3, 4}, {-1, -2, 0, 1}, {5, 6, 7, 8}, {1, 3, 2, 4}, {0, 1, 2, 3}, {5, 6, 7, 8}};
		double[][] dataResult = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}};
		final Matrix testMatrix = new Matrix("Test1", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult, testMatrix
				.reducedEchelonForm().getData());
	}

	@Test
	public void testMatrixReduceNegative() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 0, -5 }, { 0, 1, 3 }, { 0, 0, 0 } };
		final Matrix testMatrix = new Matrix("Test2", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult, testMatrix
				.reducedEchelonForm().getData());
	}

	@Test
	public void testMatrixReduceLargeValue() {
		double[][] data = { { 1586, 7546, 85746, 57564 }, { 756, 374, 385, 0 },
				{ 8765, 123, 765, 234 }, { 5736, 4736, 27364, 5 } };
		double[][] dataResult = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		final Matrix testMatrix = new Matrix("Test2", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult, testMatrix
				.reducedEchelonForm().getData());

	}
	
	@Test
	public void testERO1() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { 3, 5, 0 }, { -2, -3, 1 } };
		final Matrix testMatrix = new Matrix("Test2", data);

		assertEquals("Matrix ERO1 gives incorrect result", dataResult, testMatrix
				.ERO1(testMatrix, 2, 1).getData());
	}
	
	@Test
	public void testERO2Negative() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { 2, 3, -1 }, { 3, 5, 0 } };
		final Matrix testMatrix = new Matrix("Test2", data);

		assertEquals("Matrix ERO2 gives incorrect result", dataResult, testMatrix
				.ERO2(testMatrix, 1, -1).getData());
	}
	
	@Test
	public void testERO3Negative() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { -8, -13, 1 }, { 3, 5, 0 } };
		final Matrix testMatrix = new Matrix("Test2", data);

		assertEquals("Matrix ERO3 gives incorrect result", dataResult, testMatrix
				.ERO3(testMatrix, 1, 2, -2).getData());
	}
	
	@Test
	public void testStepOne() {
		double[][] data = { { 1, 2, 1 }, { -2, 0, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { 3, 5, 0 }, { -2, 0, 1 } };
		final Matrix testMatrix = new Matrix("Test2", data);
		
		testMatrix.stepOne(testMatrix, 1, 1);

		assertEquals("Matrix stepOne gives incorrect result", dataResult, testMatrix.getData());
	}
	
	@Test
	public void testStepOne2() {
		double[][] data = {{1.0, 0.0, -5.0}, {0.0, 1.0, 3.0}, {0.0, 0.0, 0.0}};
		double[][] dataResult = {{1.0, 0.0, -5.0}, {0.0, 1.0, 3.0}, {0.0, 0.0, 0.0}};
		final Matrix testMatrix = new Matrix("Test2", data);
		
		testMatrix.stepOne(testMatrix, 2, 2);

		assertEquals("Matrix stepOne gives incorrect result", dataResult, testMatrix.getData());
	}
	
	
	
	

}
