/**
 * Client-side validation library for Hospital Management System
 * Provides real-time form validation and error display
 */

// Regular expression patterns
const patterns = {
    email: /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
    phone: /^[0-9]{10}$/,
    name: /^[A-Za-z][A-Za-z\s]{1,49}$/,
    password: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/
};

/**
 * Validates email format
 * @param {string} email - Email to validate
 * @returns {object} Validation result
 */
function validateEmail(email) {
    if (!email || email.trim() === '') {
        return { valid: false, message: 'Email cannot be empty' };
    }
    if (!patterns.email.test(email.trim())) {
        return { valid: false, message: 'Invalid email format' };
    }
    return { valid: true, message: '' };
}

/**
 * Validates phone number
 * @param {string} phone - Phone number to validate
 * @returns {object} Validation result
 */
function validatePhone(phone) {
    if (!phone || phone.trim() === '') {
        return { valid: false, message: 'Phone number cannot be empty' };
    }
    if (!patterns.phone.test(phone.trim())) {
        return { valid: false, message: 'Phone number must be 10 digits' };
    }
    return { valid: true, message: '' };
}

/**
 * Validates name format
 * @param {string} name - Name to validate
 * @returns {object} Validation result
 */
function validateName(name) {
    if (!name || name.trim() === '') {
        return { valid: false, message: 'Name cannot be empty' };
    }
    if (!patterns.name.test(name.trim())) {
        return { valid: false, message: 'Name must contain only letters and spaces (2-50 characters)' };
    }
    return { valid: true, message: '' };
}

/**
 * Validates age
 * @param {string} age - Age to validate
 * @returns {object} Validation result
 */
function validateAge(age) {
    if (!age || age.trim() === '') {
        return { valid: false, message: 'Age cannot be empty' };
    }
    const ageNum = parseInt(age);
    if (isNaN(ageNum)) {
        return { valid: false, message: 'Age must be a valid number' };
    }
    if (ageNum < 0 || ageNum > 150) {
        return { valid: false, message: 'Age must be between 0 and 150' };
    }
    return { valid: true, message: '' };
}

/**
 * Validates salary
 * @param {string} salary - Salary to validate
 * @returns {object} Validation result
 */
function validateSalary(salary) {
    if (!salary || salary.trim() === '') {
        return { valid: false, message: 'Salary cannot be empty' };
    }
    const salaryNum = parseFloat(salary);
    if (isNaN(salaryNum)) {
        return { valid: false, message: 'Salary must be a valid number' };
    }
    if (salaryNum < 1000) {
        return { valid: false, message: 'Salary must be at least 1000' };
    }
    if (salaryNum > 10000000) {
        return { valid: false, message: 'Salary cannot exceed 10,000,000' };
    }
    return { valid: true, message: '' };
}

/**
 * Validates password strength
 * @param {string} password - Password to validate
 * @returns {object} Validation result
 */
function validatePassword(password) {
    if (!password || password.trim() === '') {
        return { valid: false, message: 'Password cannot be empty' };
    }
    if (password.length < 8) {
        return { valid: false, message: 'Password must be at least 8 characters' };
    }
    if (!patterns.password.test(password)) {
        return { valid: false, message: 'Password must contain uppercase, lowercase, and numbers' };
    }
    return { valid: true, message: '' };
}

/**
 * Validates blood group
 * @param {string} blood - Blood group to validate
 * @returns {object} Validation result
 */
function validateBloodGroup(blood) {
    if (!blood || blood.trim() === '') {
        return { valid: false, message: 'Blood group cannot be empty' };
    }
    const validGroups = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];
    if (!validGroups.includes(blood.trim().toUpperCase())) {
        return { valid: false, message: 'Invalid blood group. Must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-' };
    }
    return { valid: true, message: '' };
}

/**
 * Validates positive number
 * @param {string} value - Value to validate
 * @param {string} fieldName - Name of the field
 * @returns {object} Validation result
 */
function validatePositiveNumber(value, fieldName) {
    if (!value || value.trim() === '') {
        return { valid: false, message: fieldName + ' cannot be empty' };
    }
    const num = parseFloat(value);
    if (isNaN(num)) {
        return { valid: false, message: fieldName + ' must be a valid number' };
    }
    if (num <= 0) {
        return { valid: false, message: fieldName + ' must be a positive number' };
    }
    return { valid: true, message: '' };
}

/**
 * Displays error message for an input field
 * @param {HTMLElement} input - Input element
 * @param {string} message - Error message
 */
function showError(input, message) {
    // Remove existing error
    const existingError = input.parentElement.querySelector('.error-message');
    if (existingError) {
        existingError.remove();
    }
    
    // Add error styling
    input.style.borderColor = 'red';
    
    // Create error message element
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.style.color = 'red';
    errorDiv.style.fontSize = '14px';
    errorDiv.style.marginTop = '5px';
    errorDiv.textContent = message;
    
    // Insert error message after input
    input.parentElement.insertBefore(errorDiv, input.nextSibling);
}

/**
 * Clears error message for an input field
 * @param {HTMLElement} input - Input element
 */
function clearError(input) {
    input.style.borderColor = '';
    const errorDiv = input.parentElement.querySelector('.error-message');
    if (errorDiv) {
        errorDiv.remove();
    }
}

/**
 * Validates form on submit
 * @param {Event} event - Form submit event
 * @param {Function} customValidator - Custom validation function
 */
function validateForm(event, customValidator) {
    const form = event.target;
    let isValid = true;
    
    // Clear all previous errors
    const inputs = form.querySelectorAll('input, select, textarea');
    inputs.forEach(input => clearError(input));
    
    // Run custom validator if provided
    if (customValidator) {
        const customResult = customValidator(form);
        if (!customResult.valid) {
            event.preventDefault();
            alert(customResult.message);
            return false;
        }
    }
    
    return isValid;
}

/**
 * Adds real-time validation to an input field
 * @param {HTMLElement} input - Input element
 * @param {Function} validator - Validation function
 */
function addRealTimeValidation(input, validator) {
    input.addEventListener('blur', function() {
        const result = validator(this.value);
        if (!result.valid) {
            showError(this, result.message);
        } else {
            clearError(this);
        }
    });
    
    input.addEventListener('input', function() {
        if (this.parentElement.querySelector('.error-message')) {
            const result = validator(this.value);
            if (result.valid) {
                clearError(this);
            }
        }
    });
}

// Export for use in HTML files
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        validateEmail,
        validatePhone,
        validateName,
        validateAge,
        validateSalary,
        validatePassword,
        validateBloodGroup,
        validatePositiveNumber,
        showError,
        clearError,
        validateForm,
        addRealTimeValidation
    };
}
