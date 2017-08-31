package com.github.superzer0;

/**
 * Constant values used by TDA processing routine
 *
 * @author Jakub Kawa
 * @version 1.0
 */

public class DefaultSettings {
    /**
     * Default barcode image caption
     */
    public static final String DEFAULT_IMAGE_CAPTION = "Barcodes";

    /**
     * Default file name for persistent homology intervals saved as plain text.
     */
    public static final String DEFAULT_HOMOLOGY_FILE_NAME = "persistent_homology_summary.txt";

    /**
     * Default filename for intervals entropy computed after TDA processing finishes
     */
    public static final String DEFAULT_ENTROPY_FILE_NAME = "entropy.json";

    static final int DEFAULT_MAX_DIVISIONS = 100;
}
