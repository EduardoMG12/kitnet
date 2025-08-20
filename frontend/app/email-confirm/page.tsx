import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import EmailConfirmationPage from '@/components/pages/EmailConfirmationPage';
import React from 'react';

const EmailConfirm = () => {
    return (
        <>
            <HeaderDefault />
            <EmailConfirmationPage />
            <Footer />
        </>
    );
};

export default EmailConfirm;