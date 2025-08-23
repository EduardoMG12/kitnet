

import EmailConfirmationPage from '@/components/pages/EmailConfirmationPage';
import React, { Suspense } from 'react';

const EmailConfirm = () => {
    return (
        <>
            <Suspense fallback={<div>Loading...</div>}>
                <EmailConfirmationPage />
            </Suspense>
        </>
    );
};

export default EmailConfirm;