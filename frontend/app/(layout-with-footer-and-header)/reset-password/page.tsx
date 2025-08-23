

import ResetPasswordPage from '@/components/pages/ResetPasswordPage';
import React, { Suspense } from 'react';

const ResetPassword = () => {
    return (
        <>
            <Suspense fallback={<div>Loading...</div>}>
                <ResetPasswordPage />
            </Suspense>
        </>
    );
};

export default ResetPassword;