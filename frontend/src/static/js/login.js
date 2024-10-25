export function setupSignInSignUpListeners(styles) {
    const signInButton = document.getElementById('signIn');
    const signUpButton = document.getElementById('signUp');
    const container = document.getElementById(styles.container);

    if (signInButton && signUpButton && container) {
        signInButton.addEventListener('click', () => {
            container.classList.remove(styles.rightPanelActive);
        });

        signUpButton.addEventListener('click', () => {
            container.classList.add(styles.rightPanelActive);
        });
    }
}

export function removeSignInSignUpListeners() {
    const signInButton = document.getElementById('signIn');
    const signUpButton = document.getElementById('signUp');

    if (signInButton && signUpButton) {
        signInButton.removeEventListener('click', () => {});
        signUpButton.removeEventListener('click', () => {});
    }
}

export function goToDefaultPanel(styles){
    const container = document.getElementById(styles.container);
    if(container.classList.contains(styles.rightPanelActive)) container.classList.remove(styles.rightPanelActive);
}