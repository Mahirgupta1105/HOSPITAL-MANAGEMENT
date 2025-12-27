import java.util.regex.Pattern;

/**
 * Utility class for server-side input validation
 * Provides comprehensive validation methods for various data types
 */
public class ValidationUtils {
    
    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{10}$"  // 10 digit phone number
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[A-Za-z][A-Za-z\\s]{1,49}$"  // Letters and spaces, 2-50 chars
    );
    
    /**
     * Validates if a string is not null or empty
     * @param value Value to validate
     * @param fieldName Name of the field (for error messages)
     * @return Validation result
     */
    public static ValidationResult validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            return new ValidationResult(false, fieldName + " cannot be empty");
        }
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates email format
     * @param email Email to validate
     * @return Validation result
     */
    public static ValidationResult validateEmail(String email) {
        ValidationResult notEmptyResult = validateNotEmpty(email, "Email");
        if (!notEmptyResult.isValid()) {
            return notEmptyResult;
        }
        
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return new ValidationResult(false, "Invalid email format");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates phone number format
     * @param phone Phone number to validate
     * @return Validation result
     */
    public static ValidationResult validatePhone(String phone) {
        ValidationResult notEmptyResult = validateNotEmpty(phone, "Phone");
        if (!notEmptyResult.isValid()) {
            return notEmptyResult;
        }
        
        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            return new ValidationResult(false, "Phone number must be 10 digits");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates name format
     * @param name Name to validate
     * @return Validation result
     */
    public static ValidationResult validateName(String name) {
        ValidationResult notEmptyResult = validateNotEmpty(name, "Name");
        if (!notEmptyResult.isValid()) {
            return notEmptyResult;
        }
        
        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            return new ValidationResult(false, "Name must contain only letters and spaces (2-50 characters)");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates age range
     * @param ageStr Age as string
     * @return Validation result
     */
    public static ValidationResult validateAge(String ageStr) {
        ValidationResult notEmptyResult = validateNotEmpty(ageStr, "Age");
        if (!notEmptyResult.isValid()) {
            return notEmptyResult;
        }
        
        try {
            int age = Integer.parseInt(ageStr.trim());
            if (age < 0 || age > 150) {
                return new ValidationResult(false, "Age must be between 0 and 150");
            }
            return new ValidationResult(true, "");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Age must be a valid number");
        }
    }
    
    /**
     * Validates positive number
     * @param valueStr Value as string
     * @param fieldName Name of the field
     * @return Validation result
     */
    public static ValidationResult validatePositiveNumber(String valueStr, String fieldName) {
        ValidationResult notEmptyResult = validateNotEmpty(valueStr, fieldName);
        if (!notEmptyResult.isValid()) {
            return notEmptyResult;
        }
        
        try {
            double value = Double.parseDouble(valueStr.trim());
            if (value <= 0) {
                return new ValidationResult(false, fieldName + " must be a positive number");
            }
            return new ValidationResult(true, "");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, fieldName + " must be a valid number");
        }
    }
    
    /**
     * Validates salary amount
     * @param salaryStr Salary as string
     * @return Validation result
     */
    public static ValidationResult validateSalary(String salaryStr) {
        ValidationResult positiveResult = validatePositiveNumber(salaryStr, "Salary");
        if (!positiveResult.isValid()) {
            return positiveResult;
        }
        
        try {
            double salary = Double.parseDouble(salaryStr.trim());
            if (salary < 1000) {
                return new ValidationResult(false, "Salary must be at least 1000");
            }
            if (salary > 10000000) {
                return new ValidationResult(false, "Salary cannot exceed 10,000,000");
            }
            return new ValidationResult(true, "");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Invalid salary format");
        }
    }
    
    /**
     * Validates blood group
     * @param blood Blood group to validate
     * @return Validation result
     */
    public static ValidationResult validateBloodGroup(String blood) {
        ValidationResult notEmptyResult = validateNotEmpty(blood, "Blood group");
        if (!notEmptyResult.isValid()) {
            return notEmptyResult;
        }
        
        String[] validBloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        String bloodTrimmed = blood.trim().toUpperCase();
        
        for (String validGroup : validBloodGroups) {
            if (validGroup.equals(bloodTrimmed)) {
                return new ValidationResult(true, "");
            }
        }
        
        return new ValidationResult(false, "Invalid blood group. Must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-");
    }
    
    /**
     * Sanitizes input by removing potentially dangerous characters
     * @param input Input to sanitize
     * @return Sanitized input
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        
        // Remove HTML tags and special characters that could be used for injection
        return input.trim()
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#x27;")
                    .replaceAll("/", "&#x2F;");
    }
    
    /**
     * Inner class to represent validation results
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;
        
        public ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
