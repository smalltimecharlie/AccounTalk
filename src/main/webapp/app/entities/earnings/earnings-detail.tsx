import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './earnings.reducer';
import { IEarnings } from 'app/shared/model/earnings.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEarningsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EarningsDetail extends React.Component<IEarningsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { earningsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.earnings.detail.title">Earnings</Translate> [<b>{earningsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="grossPay">
                <Translate contentKey="accounTalkApp.earnings.grossPay">Gross Pay</Translate>
              </span>
            </dt>
            <dd>{earningsEntity.grossPay}</dd>
            <dt>
              <span id="taxDeducted">
                <Translate contentKey="accounTalkApp.earnings.taxDeducted">Tax Deducted</Translate>
              </span>
            </dt>
            <dd>{earningsEntity.taxDeducted}</dd>
            <dt>
              <span id="studentLoanDeductions">
                <Translate contentKey="accounTalkApp.earnings.studentLoanDeductions">Student Loan Deductions</Translate>
              </span>
            </dt>
            <dd>{earningsEntity.studentLoanDeductions}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.earnings.employmentDetails">Employment Details</Translate>
            </dt>
            <dd>{earningsEntity.employmentDetails ? earningsEntity.employmentDetails.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/earnings" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/earnings/${earningsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ earnings }: IRootState) => ({
  earningsEntity: earnings.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EarningsDetail);
