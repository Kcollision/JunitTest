import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.demo.Main; 

public class MainTest {
    private RealMatrix A;
    private RealMatrix AT;
    private RealMatrix nonSquareMatrix;
    private RealMatrix nonSquareTransposed;

    @Before
    public void setUp() {
        // 初始化3x3矩阵及其转置
        double[][] dataA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        A = new Array2DRowRealMatrix(dataA);
        AT = A.transpose();

        // 初始化非方阵测试数据
        double[][] nonSquareData = {{1, 2, 3}, {4, 5, 6}};
        nonSquareMatrix = new Array2DRowRealMatrix(nonSquareData);
        nonSquareTransposed = nonSquareMatrix.transpose();
    }

    @Test
    public void testTransposeSquareMatrix() {
        assertTrue(Main.test_transpose(A));
    }

    @Test
    public void testTransposeNonSquareMatrix() {
        assertTrue(Main.test_transpose(nonSquareMatrix));
    }

    @Test
    public void testMultiplyCompatibleMatrices() {
        assertTrue(Main.test_multiply(A, AT));
    }

    @Test
    public void testAddCompatibleMatrices() {
        assertTrue(Main.test_add(A, AT));
    }

    @Test
    public void testSubtractCompatibleMatrices() {
        assertTrue(Main.test_subtract(A, AT));
    }

    @Test
    public void testScalarMultiply() {
        assertTrue(Main.test_scalarMultiply(A, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplyIncompatibleDimensions() {
        RealMatrix incompatibleMatrix = new Array2DRowRealMatrix(new double[2][3]);
        Main.test_multiply(A, incompatibleMatrix);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIncompatibleDimensions() {
        RealMatrix incompatibleMatrix = new Array2DRowRealMatrix(new double[2][3]);
        Main.test_add(A, incompatibleMatrix);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractIncompatibleDimensions() {
        RealMatrix incompatibleMatrix = new Array2DRowRealMatrix(new double[2][3]);
        Main.test_subtract(A, incompatibleMatrix);
    }

    // 测试非方阵的加法兼容性
    @Test
    public void testAddNonSquareMatrices() {
        RealMatrix result = nonSquareMatrix.add(nonSquareTransposed.transpose());
        assertTrue(result.equals(nonSquareMatrix.scalarMultiply(2)));
    }
}