import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IEmpPensionContributions } from 'app/shared/model/emp-pension-contributions.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './emp-pension-contributions.reducer';

export interface IEmpPensionContributionsDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmpPensionContributionsDeleteDialog extends React.Component<IEmpPensionContributionsDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.empPensionContributionsEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { empPensionContributionsEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="accounTalkApp.empPensionContributions.delete.question">
          <Translate
            contentKey="accounTalkApp.empPensionContributions.delete.question"
            interpolate={{ id: empPensionContributionsEntity.id }}
          >
            Are you sure you want to delete this EmpPensionContributions?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-empPensionContributions" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ empPensionContributions }: IRootState) => ({
  empPensionContributionsEntity: empPensionContributions.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmpPensionContributionsDeleteDialog);
